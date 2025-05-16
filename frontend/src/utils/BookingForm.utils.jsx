import { isBefore, parse, format } from 'date-fns';

export const validateBooking = (date, startTime, endTime, occupiedIntervals) => {
    if (!date || !startTime || !endTime) {
        return { isValid: false, message: 'Пожалуйста, заполните все поля' };
    }

    const startDateTime = new Date(`${format(date, 'yyyy-MM-dd')}T${startTime}`);
    const endDateTime = new Date(`${format(date, 'yyyy-MM-dd')}T${endTime}`);

    if (isBefore(startDateTime, new Date())) {
        return { isValid: false, message: 'Ошибка: Начало бронирования не может быть в прошлом' };
    }

    if (isBefore(endDateTime, startDateTime)) {
        return { isValid: false, message: 'Ошибка: Время начала не может быть позже времени конца' };
    }

    if ((endDateTime - startDateTime) / (1000 * 60) > 90) {
        return { isValid: false, message: 'Ошибка: Бронирование не может длиться больше 90 минут' };
    }

    for (let i = 0; i < occupiedIntervals.length; i++) {
        const interval = occupiedIntervals[i];

        const intervalStartTime = parse(interval.startTime, "yyyy-MM-dd'T'HH:mm:ss", new Date());
        const intervalEndTime = parse(interval.endTime, "yyyy-MM-dd'T'HH:mm:ss", new Date());

        if (
            (startDateTime >= intervalStartTime && startDateTime <= intervalEndTime) ||
            (endDateTime >= intervalStartTime && startDateTime <= intervalEndTime) ||
            (startDateTime <= intervalStartTime && endDateTime >= intervalEndTime)
        ) {
            return { isValid: false, message: 'Ошибка: Пересечение бронирования с занятыми интервалами' };
        }
    }

    return { isValid: true, message: '' };
};