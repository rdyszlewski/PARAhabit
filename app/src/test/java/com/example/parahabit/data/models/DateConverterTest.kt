package com.example.parahabit.data.models

import com.example.parahabit.data.models.converters.DateConverter
import org.junit.Test
import org.junit.Assert;
import java.util.*

class DateConverterTest{

    @Test
    fun toNumberTest(){
        val date = getDate(2020, 11, 12)
        testToNumber(date, 201112)
    }

    private fun testToNumber(date: Date, correctNumber: Int){
        val dateValue = DateConverter.toNumber(date)
        Assert.assertEquals(correctNumber, dateValue)
    }

    fun getDate(year: Int, month: Int, day: Int): Date{
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, day)
        return calendar.time
    }

    @Test
    fun toNumberWithZerosInMonth(){
        val date = getDate(2020, 1, 10)
        testToNumber(date, 200110)
        val date2 = getDate(2020, 0, 10)
        testToNumber(date2, 200010)
    }

    @Test
    fun toNumberWithZerosInDay(){
        val date = getDate(2020, 10, 1)
        testToNumber(date, 201001)
    }

    @Test
    fun toDate(){
        val number = 201111
        val correctDate = getDate(2020, 11, 11)
        val dateValue = DateConverter.toDate(number)
        assertEqualsDate(correctDate, dateValue)
    }

    @Test
    fun toDateWithZerosInMonth(){
        val number = 200110
        val correctDate = getDate(2020, 1, 10)
        val dateValue = DateConverter.toDate(number)
        assertEqualsDate(correctDate, dateValue)

        val number2 = 200010
        val correctDate2 = getDate(2020, 0, 10)
        val dateValue2 = DateConverter.toDate(number2)
        assertEqualsDate(correctDate2, dateValue2)
    }

    private fun assertEqualsDate(correctDate: Date, date: Date){
        val correctCalendar = GregorianCalendar()
        correctCalendar.time = correctDate
        val calendar = GregorianCalendar()
        calendar.time = date

        Assert.assertEquals(correctCalendar.get(Calendar.YEAR), calendar.get(Calendar.YEAR))
        Assert.assertEquals(correctCalendar.get(Calendar.MONTH), calendar.get(Calendar.MONTH))
        Assert.assertEquals(correctCalendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.DAY_OF_MONTH))
    }


//    @Test
//    fun toNumberIncorrectValuesTest(){
//        val date = getDate(2020, 12, 1)
//        try{
//            val dateValue = DateConverter.toNumber(date)
//        } catch (e: IllegalArgumentException){
//            Assert.assertEquals("Incorrect month", e.message)
//            Assert.assertFalse(true)
//        }
//        val date2 = getDate(2020, 10, 0)
//        try {
//            val dateValue2 = DateConverter.toNumber(date2)
//            Assert.assertFalse(true)
//        } catch (e: java.lang.IllegalArgumentException){
//            Assert.assertEquals("Incorrect day", e.message)
//        }
//
//    }

    // TODO: pomyśleć, jak rozwiązać przekazywanie nieprawidłowej daty

}