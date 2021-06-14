package com.br2.cadela.workout

import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate

class SessionRepositoryTest {
    private lateinit var sessionDao: SessionDao
    private lateinit var sut: SessionRepository

    @BeforeEach
    fun setup(){
        sessionDao = spyk()
        sut = SessionRepository(sessionDao)
    }

    @Test
    fun `Load last session from Dao`() = runBlocking {
        val record: SessionRecord = SessionRecord(0, Session.FIRST_LEVEL_TEST, LocalDate.parse("2021-05-15"), LocalDate.parse("2021-05-15"))

        coEvery { sessionDao.getLastSession() } returns record
        val session = sut.getLastSession()
        coVerify { sessionDao.getLastSession() }
        assertEquals(record.session, session)
    }

    @Test
    fun `Save session`() = runBlocking {
        sut.saveSession(Session.FIRST_LEVEL_TEST)
        coVerify { sessionDao.save(SessionRecord(session = Session.FIRST_LEVEL_TEST)) }
    }
}