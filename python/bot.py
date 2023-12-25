import requests
import telebot
from telebot import types

import config

bot = telebot.TeleBot(config.BOT_TOKEN)
# Обработчик команды /start
# Словарь для хранения логинов и паролей пользователей (в реальном приложении используйте базу данных)
users_credentials = {}
logged_users = set()


# Обработчик команды /start
@bot.message_handler(commands=['start'])
def start_message(message):
    send_login_request(message)


# Обработчик текстовых сообщений
# Функция запроса логина
def send_login_request(message):
    keyboard = types.ReplyKeyboardMarkup(row_width=1, resize_keyboard=True)
    button_login = types.KeyboardButton('login')
    keyboard.add(button_login)
    msg = bot.send_message(message.chat.id, "Введите ваш логин:", reply_markup=keyboard)
    bot.register_next_step_handler(msg, process_login_step)


# Функция обработки введенного логина
def process_login_step(message):
    chat_id = message.chat.id
    users_credentials[chat_id] = message.text

    msg = bot.send_message(chat_id, "Введите ваш пароль:")
    bot.register_next_step_handler(msg, process_password_step)


# Функция обработки введенного пароля
def process_password_step(message):
    chat_id = message.chat.id
    login = users_credentials[chat_id]
    password = message.text
    # client_menu(message)
    response = requests.post("http://localhost:8080/api/login",
                             json={"login": login, "password": password, "telegramId": message.chat.id},
                             headers=config.HEADERS)
    if response.status_code == 200:
        config.ACTIVITY = True
        role = response.json().get("role")
        if role == "CLIENT":
            client_menu(message)
        elif role == "APOTHECARY":
            apothecary_menu(message)
    else:
        config.ACTIVITY = False
        bot.send_message(chat_id, response.json().get("error"))


# Обработчик нажатия на кнопки "logout" и "getorders"
@bot.message_handler(func=lambda message: message.text in ['logout', 'getorders', 'get orders', 'change_orders'])
def handle_buttons(message):
    #check login user
    if message.text == 'logout':
        config.ACTIVITY = False
        bot.send_message(message.chat.id, "Вы успешно разлогинены.", reply_markup=types.ReplyKeyboardRemove())
        send_login_request(message)

    elif message.text == 'getorders':
        if config.ACTIVITY == True:
            get_orders(message)

    elif message.text == 'get orders':
        pass
    elif message.text == 'change_orders':
        pass


# Функция получения заказов

def get_orders(message):
    print("get_orders func")
    response = requests.get(f"http://localhost:8080/api/client/{message.chat.id}/getOrders").json()

    keyboard = types.ReplyKeyboardMarkup(row_width=1, resize_keyboard=True)
    button_logout = types.KeyboardButton('logout')
    button_getorders = types.KeyboardButton('getorders')
    keyboard.add(button_logout, button_getorders)
    answer = "Ваши заказы\nid, status, price, [product name, product amount]:\n"
    for order in response.get("orders"):
        answer+= str(order.get("orderId")) + ", "+order.get("status") + ", "+str(order.get("price")) + "\n"
        for product in order.get("products"):
            answer += "    "+ product + "\n"

    bot.send_message(message.chat.id, answer, reply_markup=keyboard)

    # print(response.status_code)

def client_menu(message):
    if config.ACTIVITY:
        keyboard = types.ReplyKeyboardMarkup(row_width=1, resize_keyboard=True)
        button_logout = types.KeyboardButton('logout')
        button_getorders = types.KeyboardButton('getorders')
        keyboard.add(button_logout, button_getorders)
        bot.send_message(message.chat.id, 'Выберите действие:', reply_markup=keyboard)


def apothecary_menu(message):
    if config.ACTIVITY:

        keyboard = types.ReplyKeyboardMarkup(row_width=1, resize_keyboard=True)
        button_logout = types.KeyboardButton('logout')
        button_getorders = types.KeyboardButton('get orders')
        button_change_orders = types.KeyboardButton('change_orders')
        keyboard.add(button_logout, button_getorders, button_change_orders)
        bot.send_message(message.chat.id, 'Выберите действие:', reply_markup=keyboard)


bot.polling()
