#language: ru
Функция: Корректное добавление и удаление товаров из корзины

  @Test1
  Сценарий: Добавление товаров по нечетным номерам

    * выполнен поиск продукта "iphone"

    * загрузилась страница с результатом поиска
    * ограничить значение поля "Цена" - до "100000"
    * выбрать чекбокс "Высокий рейтинг"
    * выбрать в категории "Оперативная память" чекбокс "3 ГБ"
    * добавление 8 товаров по порядку, четные - "нет"
    * число товаров в корзине равно "8"
    * переход в корзину
    * загрузилась страница "Моя корзина"
    * проверка наличия ранее добавленных товаров в корзине
    * отчистка корзины
    * проверка, что корзина пуста

  @Test2
  Сценарий: Добавление товаров по четным номерам

    * выполнен поиск продукта "беспроводные наушники"
    * загрузилась страница с результатом поиска
    * ограничить значение поля "Цена" - до "10000"
    * открыть список вариантов у категории чекбоксов - "Бренды"
    * выбрать в категории "Бренды" чекбокс "Beats"
    * выбрать в категории "Бренды" чекбокс "Samsung"
    * выбрать чекбокс "Высокий рейтинг"
    * добавление 4 товаров по порядку, четные - "да"
    * число товаров в корзине равно "4"
    * переход в корзину
    * загрузилась страница "Моя корзина"
    * проверка наличия ранее добавленных товаров в корзине
    * отчистка корзины
    * проверка, что корзина пуста