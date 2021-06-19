# slotegrator

Для запуска необходима Java не ниже 8 и Maven.

Также необходимо любое из двух условий:
* либо положить **chromedriver.exe** в директорию /dev/chromedriver;
* либо добавить chromedriver в переменную окружения Path и в SeleniumUiTest закомментировать строку
`System.setProperty(CHROME, DRIVER_ROOT);`
  в SeleniumUiTest

Запуск тестов можно произвести командой:

`mvn test -Dtest=tests.**`

Запуск тестов на Cucumber производится непосредственно из фич в директории /resources/features

Директория **tests** содержит:

* /api/ApiTest - тесты по апи из тестового задания
* /ui/selenide/SelenideUiTest - UI тесты на селениде с небольшим Page Object
* /ui/selenium/SeleniumUiTest - UI тесты на голом селениуме

Директория **resources** содержит:

* /features/login.feature - сценарий запуска UI теста на авторизацию в связке с Cucumber
* /features/playersPage.feature - сценарии запуска UI тестов на страницу с таблицей игроков в связке с Cucumber
* /schema/playerSchema.json - схема json для валидации в ходе апи тестов


