import json
import time
import pandas as pd
from kafka import KafkaProducer

def send_to_kafka(row):
    return {
        "sale_id": row['id'],
        "customer_id": row['sale_customer_id'],
        "product_id": row['sale_product_id'],
        "quantity": row['sale_quantity'],
        "total_price": row['sale_total_price'],
        "sale_date": row['sale_date'],
        "customer_info": {
            "first_name": row['customer_first_name'],
            "last_name": row['customer_last_name'],
            "age": row['customer_age'],
            "email": row['customer_email']
        },
        "product_info": {
            "name": row['product_name'],
            "category": row['product_category'],
            "price": row['product_price']
        }
    }

def produce_data():
    producer = KafkaProducer(
        bootstrap_servers='kafka:9092',
        value_serializer=lambda v: json.dumps(v).encode('utf-8')
    )
    
    for i in range(1, 11):
        df = pd.read_csv(f'/data/mock_data{i}.csv')
        for _, row in df.iterrows():
            producer.send('sales-topic', send_to_kafka(row))
            time.sleep(0.01)

if __name__ == "__main__":
    produce_data()