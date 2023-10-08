import pandas as pd
from sklearn.model_selection import train_test_split
import matplotlib.pyplot as plt

from regression import linear_regression, predict, r2_score, sum_of_squares
import numpy as np

# Загрузка данных
pd.set_option('display.max_columns', None)
data = pd.read_csv('california_housing_train.csv')

# Синтаксический признак
data['expensive_car'] = data['population'] + data['housing_median_age'] + data['median_income']

print(data.head())

# Получение статистики
statistics = data.describe()
print(statistics)

#Визуализация статистики по датасету
data.hist(bins=60, figsize=(10, 10))
plt.show()

# Визуализация количества
plt.figure(figsize=(15, 10))
# data.count().plot(kind='bar', title='Количество')
plt.title('Количество')
dfCount = data.count()
plt.barh(dfCount.index, dfCount.values)
plt.xlim(dfCount.count(), 17000)
plt.show()

# Визуализация средних значений
plt.figure(figsize=(15, 10))
plt.title('Средние значения признаков')
dfMeans = data.mean()
plt.barh(dfMeans.index, dfMeans.values)
plt.xlim(dfMeans.min(), 3000)
plt.show()

# Визуализация стандартных отклонений
plt.figure(figsize=(15, 10))
plt.title('Стандартные отклонения признаков')
dfStd = data.std()
plt.barh(dfStd.index, dfStd.values)
plt.xlim(dfStd.min(), 3000)
plt.show()

# Визуализация минимума
plt.figure(figsize=(15, 10))
plt.title('Минимум')
dfMin = data.min()
plt.barh(dfMin.index, dfMin.values)
plt.xlim(dfMin.min()*1.1, 300)
plt.show()

# Визуализация максимума
plt.figure(figsize=(15, 10))
plt.title('Максимум')
dfMax = data.max()
plt.barh(dfMax.index, dfMax.values)
plt.xlim(dfMax.min() * 1.1, 400)
plt.show()


# Проверка наличия отсутствующих значений
missing_values = data.isnull().sum()
print(missing_values)

# Заполнение отсутствующих значений средними значениями
data.fillna(data.mean(), inplace=True)

# Нормировка данных
for column in ['longitude', 'latitude', 'housing_median_age', 'total_rooms', 'total_bedrooms', 'population', 'households', 'median_income']:
    mean = data[column].mean()
    std = data[column].std()
    data[column] = (data[column] - mean) / std


# Определите признаки (X) и целевую переменную (y)
X = data.drop(columns=['median_house_value'])  # Признаки, исключая 'median_house_value'
y = data['median_house_value']  # Целевая переменная 'median_house_value'

# Разделите данные на обучающий и тестовый наборы данных
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)
# coefficients = linear_regression(X_train.values, y_train.values)
# print(coefficients)


# Модель 1 по всем признакам
columns = ['longitude', 'latitude', 'housing_median_age', 'total_rooms', 'total_bedrooms', 'population', 'households', 'median_income']
X_train_1, X_test_1, y_train, y_test = train_test_split(data[columns], y, test_size=0.2, random_state=42)
coefficients1 = linear_regression(X_train_1.values, y_train.values)
y_pred_1 = predict(X_test_1.values, coefficients1)
print('Предсказания модели 1:', y_pred_1)
print('Коэффициент детерминации:', r2_score(y_test, y_pred_1),'\n')


# Модель 2 по признакам total_rooms и total_bedrooms
columns = ['total_rooms', 'total_bedrooms']
X_train_2, X_test_2, y_train, y_test = train_test_split(data[columns], y, test_size=0.2, random_state=42)
coefficients2 = linear_regression(X_train_2.values, y_train.values)
y_pred_2 = predict(X_test_2.values, coefficients2)
print('Предсказания модели 2:', y_pred_2)
print('Коэффициент детерминации:', r2_score(y_test, y_pred_2),'\n')


# Модель 3 по признаку housing_median_age
columns = ['median_income']
X_train_3, X_test_3, y_train, y_test = train_test_split(data[columns], y, test_size=0.2, random_state=42)
coefficients3 = linear_regression(X_train_3.values, y_train.values)
y_pred_3 = predict(X_test_3.values, coefficients3)
print('Предсказания модели 3:', y_pred_3)
print('Коэффициент детерминации:', r2_score(y_test, y_pred_3))

