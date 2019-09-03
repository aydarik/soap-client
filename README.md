### Сборка
* Настроить параметры в `application.properties`
    * ```
      # Internal settings
      file-reader.buffer-size=65536
      file-reader.files.location=E:\\files\\
      file-reader.files.extension=txt
      ```
* Выполнить `./mvnw clean package`

### Подготовка данных
* Запустить контейнер PostgreSQL
    ```
    docker run --name postgres -p 5432:5432 -e POSTGRES_PASSWORD=postgres -d postgres
    ```

* Сгенерировать данные
    * открыть файл `generate.sh` и настроить параметры генерации данных
    ```
    FILES_CNT=20
    DIR='/e/files'
    MAX_NUMBERS=30000000
    MAX_VALUES=999999999
    ```
    * запустить `./generate.sh`
    
* Схема БД будет сгенерирована автоматически при старте приложения

### Запуск
* Запусть собранный артефакт
    ```
    java -jar target/soap-client-1.0.1.jar
    ```

* Ввести искомое число в файле `request.xml`

* Выполнить запрос
    ```
    curl --header "content-type: text/xml" -d @request.xml http://localhost:8080/v1/filesearch
    ```

* Результат запроса вернется в виде ответа сервиса и будет записан в БД в таблице `results`