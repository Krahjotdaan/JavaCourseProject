import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { format } from 'date-fns';
import {
    FormLabel,
    FormInput,
    FormSelect,
    SubmitButton,
    OccupiedIntervalsList,
    OccupiedIntervalItem,
} from '../styles/BookingForm.styled';
import { Message } from '../styles/themes';
import { validateBooking } from '../utils/BookingForm.utils';

const BookingForm = () => {
    const [date, setDate] = useState(new Date());
    const [startTime, setStartTime] = useState('');
    const [endTime, setEndTime] = useState('');
    const [workspaceId, setWorkspaceId] = useState('');
    const [email, setEmail] = useState('');
    const [message, setMessage] = useState(null);
    const [occupiedIntervals, setOccupiedIntervals] = useState([]);
    const [availableWorkspaces, setAvailableWorkspaces] = useState([]);

    useEffect(() => {
        fetchWorkspaces();
    }, []);

    useEffect(() => {
        if (workspaceId && date) {
            fetchOccupiedIntervals();
        }
    }, [workspaceId, date]);

    const fetchWorkspaces = async () => {
        try {
            const apiUrl = `/api/workspaces`;
            const response = await axios.get(apiUrl);

            setAvailableWorkspaces(response.data.data);
        } 
        catch (error) {
            setMessage({ text: 'Ошибка при получении рабочих пространств', type: 'error' });
            setAvailableWorkspaces([]);
        } 
    };

    const fetchOccupiedIntervals = async () => {
        try {
            const formattedDate = format(date, 'yyyy-MM-dd');
            const apiUrl = `/api/bookings/occupied?workspaceId=${workspaceId}&date=${formattedDate}`;
            const response = await axios.get(apiUrl);

            setOccupiedIntervals(response.data);
        } 
        catch (error) {
            setMessage({ text: `Ошибка при получении занятых интервалов`, type: 'error' });
        }
    };

    useEffect(() => {
        if (message) {
            const timer = setTimeout(() => {
                setMessage(null);
            }, 5000);
            return () => clearTimeout(timer);
        }
    }, [message]);

    const handleSubmit = async (e) => {
        e.preventDefault();

        const { isValid, message: validationMessage } = validateBooking(
            date,
            startTime,
            endTime,
            occupiedIntervals
        );

        if (!isValid) {
            setMessage({ text: validationMessage, type: 'error' });
            return;
        }

        try {
            const startDateTime = new Date(`${format(date, 'yyyy-MM-dd')}T${startTime}`);
            const endDateTime = new Date(`${format(date, 'yyyy-MM-dd')}T${endTime}`);

            const bookingData = {
                userEmail: email,
                workspaceId: workspaceId,
                startTime: startDateTime.toISOString(),
                endTime: endDateTime.toISOString(),
            };

            const apiUrl = `/api/bookings/create`;
            const response = await axios.post(apiUrl, bookingData);

            if (response.status === 200 || response.status === 201) {
                setMessage({ text: 'Бронирование успешно создано', type: 'success' });
                fetchOccupiedIntervals();
            } 
            else {
                setMessage({ text: `Не удалось создать бронирование`, type: 'error' });
            }
        } 
        catch (error) {
            let errorMessage = '';

            if (error.response) {
                errorMessage = 'Неизвестная ошибка при создании бронирования';
            } 
            else if (error.request) {
                errorMessage = 'Не удалось связаться с сервером.';
            }
            setMessage({ text: errorMessage, type: 'error' });
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            {message && (
                <Message type={message.type}>
                    {message.text}
                </Message>
            )}
            <FormLabel htmlFor="date">
                Дата:
                <FormInput type="date" id="date" value={format(date, 'yyyy-MM-dd')} onChange={(e) => setDate(new Date(e.target.value))} />
            </FormLabel>
            <FormLabel htmlFor="workspaceId">
                Рабочее пространство:
                <FormSelect id="workspaceId" value={workspaceId} onChange={(e) => setWorkspaceId(e.target.value)} disabled={!date}>
                    <option value="">Выберите рабочее пространство</option>
                    {availableWorkspaces.map(workspace => (
                        <option key={workspace.id} value={workspace.id}>{workspace.id}</option>
                    ))}
                </FormSelect>
            </FormLabel>
            {occupiedIntervals.length > 0 && (
                <>
                    <h3>Занятые интервалы:</h3>
                    <OccupiedIntervalsList>
                        {occupiedIntervals.map(interval => (
                            <OccupiedIntervalItem key={interval.id}>
                                {format(new Date(interval.startTime), 'HH:mm')} - {format(new Date(interval.endTime), 'HH:mm')}
                            </OccupiedIntervalItem>
                        ))}
                    </OccupiedIntervalsList>
                </>
            )}
            <FormLabel htmlFor="startTime">
                Начало бронирования:
                <FormInput type="time" id="startTime" value={startTime} onChange={e => setStartTime(e.target.value)} disabled={!workspaceId} />
            </FormLabel>
            <FormLabel htmlFor="endTime">
                Конец бронирования:
                <FormInput type="time" id="endTime" value={endTime} onChange={e => setEndTime(e.target.value)} disabled={!workspaceId} />
            </FormLabel>
            <FormLabel htmlFor="email">
                Email:
                <FormInput type="email" id="email" value={email} onChange={(e) => setEmail(e.target.value)} />
            </FormLabel>
            <SubmitButton type="submit" disabled={!startTime || !endTime || !email}>Создать бронирование</SubmitButton>
        </form>
    );
};

export default BookingForm;