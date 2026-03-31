package com.example.dentapp.domain.usecase.patients.constants

object PatientsErrors {
    const val INVALID_EMAIL = "Неверный формат email"
    const val FULL_NAME_TOO_LONG = "ФИО должно содержать не более 100 символов"

    const val CONTACT_INFO_REQUIRED = "Необходимо заполнить хотя бы одно поле: почта или номер телефона"
    const val FIELDS_REQUIRED = "Пожалуйста, заполните все обязательные поля"
    const val NOT_SAVED = "Не удалось сохранить пациент"
}