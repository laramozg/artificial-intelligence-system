import numpy as np


def linear_regression(X_train, y_train):
    # Добавим столбец с единицами для учёта свободного члена
    X = np.column_stack((np.ones(X_train.shape[0]), X_train))

    # Вычислим коэффициентов линейной регрессии методом наименьших квадратов
    coefficients = np.linalg.inv(X.T.dot(X)).dot(X.T).dot(y_train)

    return coefficients


# Получим предсказания для тестового набора данных
def predict(X_test, coefficients):
    # Добавим столбец с единицами для учёта свободного члена
    X = np.column_stack((np.ones(X_test.shape[0]), X_test))

    # Предскажем значения
    y_pred = X.dot(coefficients)
    return y_pred


# Оценка производительности с использованием коэффициента детерминации (R^2)
def r2_score(y_test, y_pred):
    total_variance = np.sum((y_test - np.mean(y_test)) ** 2)
    residual_variance = np.sum((y_test - y_pred) ** 2)
    r2 = 1 - (residual_variance / total_variance)
    return r2
