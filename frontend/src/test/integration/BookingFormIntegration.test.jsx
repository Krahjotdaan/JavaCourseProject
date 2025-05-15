import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import axios from 'axios';
import BookingForm from '../../components/BookingForm';

jest.mock('axios');

describe('BookingForm Integration Tests', () => {
  beforeEach(() => {
    axios.get.mockResolvedValue({ data: { data: [{ id: 'workspace1' }] } });
  });

  it('should create a booking', async () => {
    axios.post.mockResolvedValue({ status: 201, data: { id: 1 } });

    render(<BookingForm />);

    fireEvent.change(screen.getByLabelText(/Дата/i), { target: { value: '2023-10-01' } });
    fireEvent.change(screen.getByLabelText(/Рабочее пространство/i), { target: { value: 'workspace1' } });
    fireEvent.change(screen.getByLabelText(/Начало бронирования/i), { target: { value: '10:00' } });
    fireEvent.change(screen.getByLabelText(/Конец бронирования/i), { target: { value: '11:00' } });
    fireEvent.change(screen.getByLabelText(/Email/i), { target: { value: 'test@example.com' } });

    fireEvent.click(screen.getByText(/Создать бронирование/i));

    await waitFor(() => {
      expect(screen.getByText(/Бронирование успешно создано/i)).toBeInTheDocument();
    });
  });
});