package com.example.furstychristmas.domain.info.usecase

import com.example.furstychristmas.domain.info.repository.InfoRepository
import java.time.LocalDate

class LoadInfoUseCase(private val infoRepository: InfoRepository) {
    fun getInfoAtDay(localDate: LocalDate) = infoRepository.getContent().firstOrNull { it.date == localDate }
}