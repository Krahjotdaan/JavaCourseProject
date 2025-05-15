import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import axios from 'axios';
import BookingList from '../../components/BookingList';

jest.mock('axios');

describe('BookingList Integration Tests', () => {
  beforeEach(() => {
    axios.get.mockResolvedValue({ data: [{ id: 1, workspaceId: 'workspace1', startTime: '2023-10-01T10:00:00', endTime: '2023-10-01T11:00:00' }] });
  });

  it('should fetch and display bookings by email', async () => {
    render(<BookingList />);

    fireEvent.change(screen.getByPlaceholderText(/Введите свой email/i), { target: { value: 'test@example.com' } });
    fireEvent.click(screen.getByText(/Найти/i));

    await waitFor(() => {
      expect(screen.getByText(/workspace1 - 01.10.2023 10:00 - 11:00/i)).toBeInTheDocument();
    });
  });
});