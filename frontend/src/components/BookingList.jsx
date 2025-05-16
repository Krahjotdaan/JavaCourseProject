import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { format, isPast } from 'date-fns';
import {
    BookingListContainer,
    BookingItem,
    DeleteButton,
    SearchContainer,
    SearchInput,
    SearchButton,
    BookingInfo,
} from '../styles/BookingList.styled';
import { Message } from '../styles/themes';

const BookingList = () => {
    const [email, setEmail] = useState('');
    const [bookings, setBookings] = useState([]);
    const [message, setMessage] = useState(null);

    useEffect(() => {
            if (message) {
                const timer = setTimeout(() => {
                    setMessage(null);
                }, 5000);
                return () => clearTimeout(timer);
            }
        }, [message]);

    const handleSearch = async () => {
        try {
            const response = await axios.get(`/api/bookings/byEmail?email=${email}`);
            setBookings(response.data);
        } 
        catch (error) {
            setMessage({ text: 'Ошибка при получении бронирований', type: 'error' });
        }
    };

    const handleDelete = async (bookingId) => {
        try {
            await axios.delete(`/api/bookings/${bookingId}`);
            handleSearch();
        } 
        catch (error) {
            setMessage({ text: 'Ошибка при удалении бронирований', type: 'error' });
        }
    };

    return (
        <BookingListContainer>
            <h2>Ваши бронирования</h2>
            {message && (
                <Message type={message.type}>
                    {message.text}
                </Message>
            )}
            <SearchContainer>
                <SearchInput
                    type="email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    placeholder="Введите свой email"
                />
                <SearchButton onClick={handleSearch}>Найти</SearchButton>
            </SearchContainer>
            <ul>
                {bookings.map((booking) => (
                    !isPast(new Date(booking.endTime)) && (
                        <BookingItem key={booking.id}>
                            <BookingInfo>
                                {booking.workspaceId} - {format(new Date(booking.startTime), 'dd.MM.yyyy HH:mm')} - {format(new Date(booking.endTime), 'HH:mm')}
                            </BookingInfo>
                            <DeleteButton onClick={() => handleDelete(booking.id)}>
                                Удалить
                            </DeleteButton>
                        </BookingItem>
                    )
                ))}
            </ul>
        </BookingListContainer>
    );
};

export default BookingList;