import React, { useState } from 'react';
import axios from 'axios';
import { format, isPast } from 'date-fns';
import styled from 'styled-components';

const BookingListContainer = styled.div`
  width: 80%;
  max-width: 600px;
  margin-top: 20px;
`;

const BookingItem = styled.li`
  padding: 5px 10px;
  border-radius: 4px;
  background-color: ${({ theme }) => theme.occupiedIntervalBackground};
  color: ${({ theme }) => theme.text};
  margin-bottom: 5px;
`;

const SearchContainer = styled.div`
  display: flex;
  align-items: center;
  margin-bottom: 10px;
`;

const SearchInput = styled.input`
  padding: 8px;
  border: 1px solid ${({ theme }) => theme.borderColor};
  border-radius: 4px;
  margin-right: 10px;
  flex-grow: 1; 
  color: ${({ theme }) => theme.text};
  background-color: ${({ theme }) => theme.inputBackground};
`;

const SearchButton = styled.button`
  padding: 8px 12px;
  border: none;
  border-radius: 4px;
  background-color: ${({ theme }) => theme.toggleButtonBackground};
  color: ${({ theme }) => theme.buttonText};
  cursor: pointer;
`;

const BookingList = () => {
    const [email, setEmail] = useState('');
    const [bookings, setBookings] = useState([]);

    const handleSearch = async () => {
        try {
            const response = await axios.get(`/api/bookings/byEmail?email=${email}`);
            setBookings(response.data);
        } 
        catch (error) {
            console.error('Error fetching bookings:', error);
        }
    };

    return (
        <BookingListContainer>
            <h2>Ваши бронирования</h2>
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
                            {booking.workspaceId} - {format(new Date(booking.startTime), 'dd.MM.yyyy HH:mm')} - {format(new Date(booking.endTime), 'HH:mm')}
                        </BookingItem>
                    )
                ))}
            </ul>
        </BookingListContainer>
    );
};

export default BookingList;