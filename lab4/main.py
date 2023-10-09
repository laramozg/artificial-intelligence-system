import pandas as pd
from sklearn.model_selection import train_test_split
import matplotlib.pyplot as plt
import seaborn as sb
from regression import linear_regression, predict, r2_score


# Загрузка данных
pd.set_option('display.max_columns', None)
data = pd.read_csv('california_housing_train.csv')

#data['expensive_car'] = data['population'] + data['housing_median_age'] + data['median_income']

print(data.head())

# Получение статистики
statistics = data.describe()
print(statistics)

#Визуализация статистики по датасету
data.hist(bins=60, figsize=(10, 10))
plt.show()

# Корреляция
plt.figure(figsize=(12,10), dpi= 80)
sb.heatmap(data.corr(), xticklabels=data.corr().columns, yticklabels=data.corr().columns, cmap='RdYlGn', center=0, annot=True)


plt.title('Корреляция', fontsize=22)
plt.xticks(fontsize=12)
plt.yticks(fontsize=12)
plt.show()
corr = data.corr()
print(corr)

data = data.drop(columns=['total_bedrooms'])
data = data.drop(columns=['households'])



# Проверка наличия отсутствующих значений
missing_values = data.isnull().sum()
print(missing_values)

# Заполнение отсутствующих значений средними значениями
data.fillna(data.mean(), inplace=True)

# Нормировка данных
for column in [ 'housing_median_age', 'total_rooms', 'population', 'median_income']:
    median = data[column].median()
    range_value = data[column].max() - data[column].min()
    data[column] = (data[column] - median) / range_value


# Определите признаки (X) и целевую переменную (y)
X = data.drop(columns=['median_house_value'])  # Признаки, исключая 'median_house_value'
y = data['median_house_value']  # Целевая переменная 'median_house_value'

# Разделите данные на обучающий и тестовый наборы данных
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)


# Модель 1 по всем признакам
columns = ['longitude', 'latitude', 'housing_median_age', 'total_rooms',  'population','median_income']
data = data.drop(columns=['median_house_value'])

X_train_1, X_test_1, y_train, y_test = train_test_split(data[columns], y, test_size=0.2, random_state=42)
coefficients1 = linear_regression(X_train_1.values, y_train.values)
y_pred_1 = predict(X_test_1.values, coefficients1)
print('Предсказания модели 1:', y_pred_1)
print('Коэффициент детерминации:', r2_score(y_test, y_pred_1),'\n')


# Модель 2 по признаку total_rooms
columns = ['total_rooms']
X_train_2, X_test_2, y_train, y_test = train_test_split(data[columns], y, test_size=0.2, random_state=42)
coefficients2 = linear_regression(X_train_2.values, y_train.values)
y_pred_2 = predict(X_test_2.values, coefficients2)
print('Предсказания модели 2:', y_pred_2)
print('Коэффициент детерминации:', r2_score(y_test, y_pred_2),'\n')


# Модель 3 по признаку median_income
columns = ['median_income']
X_train_3, X_test_3, y_train, y_test = train_test_split(data[columns], y, test_size=0.2, random_state=42)
coefficients3 = linear_regression(X_train_3.values, y_train.values)
y_pred_3 = predict(X_test_3.values, coefficients3)
print('Предсказания модели 3:', y_pred_3)
print('Коэффициент детерминации:', r2_score(y_test, y_pred_3))

