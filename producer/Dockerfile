# producer/Dockerfile
FROM python:3.9-slim

# Установите необходимые зависимости
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

# Копируем скрипт продюсера
COPY producer.py .

# Устанавливаем команду по умолчанию
CMD ["python", "producer.py"]