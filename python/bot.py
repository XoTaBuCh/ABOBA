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
    msg = bot.send_message(message.chat.id, "Введите ваш логин:")
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
    response = requests.post("http://localhost:8080/api/login",
                             json={"login": login, "password": password, "telegramId": message.chat.id},
                             headers=config.HEADERS)
    if response.status_code == 200:
        role = response.json().get("role")
        if role == "CLIENT":
            client_menu(message)
        elif role == "APOTHECARY":
            apothecary_menu()
    else:
        bot.send_message(chat_id, response.json().get("error"))


# Обработчик нажатия на кнопки "logout" и "getorders"
@bot.message_handler(func=lambda message: message.text in ['logout', 'getorders'])
def handle_buttons(message):
    if message.chat.id in logged_users:
        if message.text == 'logout':
            logged_users.remove(message.chat.id)
            send_login_request(message)
        elif message.text == 'getorders':
            get_orders(message)


# Функция получения заказов
def get_orders(message):
    # Реализуйте здесь логику получения заказов
    pass  # Пока просто оператор pass


def client_menu(message):
    keyboard = types.ReplyKeyboardMarkup(row_width=1, resize_keyboard=True)
    button_logout = types.KeyboardButton('logout')
    button_getorders = types.KeyboardButton('getorders')
    keyboard.add(button_logout, button_getorders)
    bot.send_message(message.chat.id, 'Выберите действие:', reply_markup=keyboard)


def apothecary_menu(message):
    keyboard = types.ReplyKeyboardMarkup(row_width=1, resize_keyboard=True)
    button_logout = types.KeyboardButton('logout')
    button_getorders = types.KeyboardButton('getorders')
    button_change_orders = types.KeyboardButton('change_orders')
    keyboard.add(button_logout, button_getorders, button_change_orders)
    bot.send_message(message.chat.id, 'Выберите действие:', reply_markup=keyboard)


bot.polling()


def get_orders(message):
    response = requests.get(f"localhost:8080/client/{message.chat.id}/getOrders").json()

    keyboard = types.ReplyKeyboardMarkup(row_width=1, resize_keyboard=True)
    button_logout = types.KeyboardButton('logout')
    button_getorders = types.KeyboardButton('getorders')
    keyboard.add(button_logout, button_getorders)

    bot.send_message(message.chat.id, response.text, reply_markup=keyboard)

    print(response.status_code)


# Запуск бота
bot.polling()
