## Предварительные требования
*   Python 3.9+
*   Java JDK 11+ и Maven (для сборки Flink job)
*   Docker и Docker Compose 

1.  **Сборка Flink job:**
    Перейдите в папку flink-job и выполните:
    ```bash
    mvn clean package -DskipTests
    ```
    *После сборки JAR-файл появится в docker/flink/job/*

2. **Запуск всей среды через Docker Compose**
  Из корня проекта выполните команду:
   ```bash
   docker-compose up --build -d
   ```
   * Это запустит следующие сервисы:
     * `Zookeeper и Kafka (для брокера сообщений)`
     * `PostgreSQL с инициализацией схемы из init.sql`
     * `Flink JobManager и TaskManager для обработки данных`
     * `Producer (отправляет CSV-данные в Kafka топик sales-topic)`


3.  **Проверка работы**
* Откройте веб-интерфейс Flink: http://localhost:8081
Убедитесь, что job запускается и активен.

* Подключитесь к PostgreSQL (порт 5438)

* Выполните SQL-запросы для проверки данных:
  ```bash
  SELECT * FROM fact_sales LIMIT 10;
  SELECT COUNT(*) FROM dim_customer;
  SELECT COUNT(*) FROM dim_product;
  SELECT COUNT(*) FROM dim_time;
   ```

## Остановка проекта

1.  **Остановить и удалить контейнеры:**
    ```bash
    docker-compose down -v (удаление томов)
    ```
---
