import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { format } from 'date-fns';
import styled from 'styled-components';

const FormLabel = styled.label`
  display: flex;
  flex-direction: column;
  margin-bottom: 10px;
  color: ${({ theme }) => theme.text};
`;

const FormInput = styled.input`
  padding: 8px;
  margin-top: 5px;
  border: 1px solid ${({ theme }) => theme.borderColor};
  border-radius: 4px;
  background-color: ${({ theme }) => theme.inputBackground};
  color: ${({ theme }) => theme.text};
`;

const FormSelect = styled.select`
  padding: 8px;
  margin-top: 5px;
  border: 1px solid ${({ theme }) => theme.borderColor};
  border-radius: 4px;
  background-color: ${({ theme }) => theme.inputBackground};
  color: ${({ theme }) => theme.text};
`;

const SubmitButton = styled.button`
  padding: 10px 15px;
  border: none;
  border-radius: 4px;
  background-color: ${({ theme }) => theme.toggleButtonBackground};
  color: ${({ theme }) => theme.buttonText};
  cursor: pointer;
  margin-top: 20px;
  width: 100%;
`;

const Message = styled.div`
  padding: 10px;
  border-radius: 4px;
  margin-bottom: 10px;
  text-align: center;
  color: ${({ theme, type }) => (type === 'success' ? theme.successText : theme.errorText)};
  background-color: ${({ theme, type }) => (type === 'success' ? theme.successBackground : theme.errorBackground)};
`;

const OccupiedIntervalsList = styled.ul`
  list-style: none;
  padding: 0;
  margin-top: 10px;
`;

const OccupiedIntervalItem = styled.li`
  padding: 5px 10px;
  border-radius: 4px;
  background-color: ${({ theme }) => theme.occupiedIntervalBackground};
  color: ${({ theme }) => theme.text};
  margin-bottom: 5px;
`;

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
        if (workspaceId && date) {
            fetchOccupiedIntervals();
        }
    }, [workspaceId, date]);

    const fetchOccupiedIntervals = async () => {
        try {
            const formattedDate = format(date, 'yyyy-MM-dd');
            const response = await axios.get(`/bookings/occupied?workspaceId=${workspaceId}&date=${formattedDate}`);
            console.log("Response from backend:", response.data);
            setOccupiedIntervals(response.data);
        } catch (error) {
            console.error('Error fetching occupied intervals:', error);
            setMessage({ text: 'Ошибка получения занятых интервалов', type: 'error' });
        }
    };

    useEffect(() => {
        if (message) {
            const timer = setTimeout(() => {
                setMessage(null);
            }, 3000);
            return () => clearTimeout(timer);
        }
    }, [message]);

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            const startDateTime = new Date(date);
            const [hours, minutes] = startTime.split(':');
            startDateTime.setHours(hours);
            startDateTime.setMinutes(minutes);

            const endDateTime = new Date(date);
            const [endHours, endMinutes] = endTime.split(':');
            endDateTime.setHours(endHours);
            endDateTime.setMinutes(endMinutes);

            const bookingData = {
                userId: 1,
                workspaceId: workspaceId,
                startTime: format(startDateTime, 'yyyy-MM-dd\'T\'HH:mm:ss'),
                endTime: format(endDateTime, 'yyyy-MM-dd\'T\'HH:mm:ss'),
            };

            const response = await axios.post('/bookings/create', bookingData);
            setMessage({ text: 'Бронирование создано', type: 'success' });
        } catch (error) {
            setMessage({ text: error.response?.data?.error || 'Ошибка в создании бронирования', type: 'error' });
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            {message && (
                <Message type={message.type}>
                    {message.text}
                </Message>
            )}
            <FormLabel>
                Дата:
                <FormInput type="date" value={format(date, 'yyyy-MM-dd')} onChange={(e) => setDate(new Date(e.target.value))}  />
            </FormLabel>
            <FormLabel>
                Рабочее пространство:
                <FormSelect value={workspaceId} onChange={(e) => setWorkspaceId(e.target.value)} disabled={!date}>
                    <option value="">Выберите рабочее пространство</option>
                    {availableWorkspaces.map(workspace => (
                        <option key={workspace.id} value={workspace.id}>{workspace.name}</option>
                    ))}
                </FormSelect>
            </FormLabel>
            {occupiedIntervals.length > 0 && (
                <div>
                    <h3>Занятые интервалы:</h3>
                    <OccupiedIntervalsList>
                        {occupiedIntervals.map(interval => (
                            <OccupiedIntervalItem key={interval.id}>
                                {format(new Date(interval.startTime), 'HH:mm')} - {format(new Date(interval.endTime), 'HH:mm')}
                            </OccupiedIntervalItem>
                        ))}
                    </OccupiedIntervalsList>
                </div>
            )}
            <FormLabel>
                Начало бронирования:
                <FormInput type="time" value={startTime} onChange={e => setStartTime(e.target.value)} disabled={!workspaceId} />
            </FormLabel>
            <FormLabel>
                Конец бронирования:
                <FormInput type="time" value={endTime} onChange={e => setEndTime(e.target.value)} disabled={!workspaceId} />
            </FormLabel>
            <FormLabel>
                Email:
                <FormInput type="email" value={email} onChange={(e) => setEmail(e.target.value)} />
            </FormLabel>
            <SubmitButton type="submit" disabled={!startTime || !endTime}>Создать бронирование</SubmitButton>
        </form>
    );
};

export default BookingForm;