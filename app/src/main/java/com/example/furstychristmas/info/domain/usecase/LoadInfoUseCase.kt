package com.example.furstychristmas.info.domain.usecase

import com.example.furstychristmas.info.domain.repository.InfoRepository
import java.time.LocalDate

class LoadInfoUseCase(private val infoRepository: InfoRepository) {
    fun getInfoAtDay(localDate: LocalDate) = infoRepository.getContent().firstOrNull { it.date == localDate }
}