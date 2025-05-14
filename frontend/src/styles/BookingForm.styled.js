import styled from 'styled-components';

export const BookingFormContainer = styled.div`
  border: 1px solid ${({ theme }) => theme.borderColor};
  border-radius: 8px;
  padding: 20px;
  margin-bottom: 20px;
  width: 100%;
  max-width: 600px;
  display: flex;
  flex-direction: column;
  box-sizing: border-box; 
`;

export const FormLabel = styled.label`
  display: flex;
  flex-direction: column;
  margin-bottom: 10px;
  color: ${({ theme }) => theme.text};
`;

export const FormInput = styled.input`
  padding: 8px;
  margin-top: 5px;
  border: 1px solid ${({ theme }) => theme.borderColor};
  border-radius: 4px;
  background-color: ${({ theme }) => theme.inputBackground};
  color: ${({ theme }) => theme.text};
  width: 100%;
  box-sizing: border-box; 
`;

export const FormSelect = styled.select`
  padding: 8px;
  margin-top: 5px;
  border: 1px solid ${({ theme }) => theme.borderColor};
  border-radius: 4px;
  background-color: ${({ theme }) => theme.inputBackground};
  color: ${({ theme }) => theme.text};
  width: 100%; 
  box-sizing: border-box; 
`;

export const SubmitButton = styled.button`
  padding: 10px 15px;
  border: none;
  border-radius: 4px;
  background-color: ${({ theme }) => theme.toggleButtonBackground};
  color: ${({ theme }) => theme.buttonText};
  cursor: pointer;
  margin-top: 20px;
  width: 100%;
`;

export const OccupiedIntervalsList = styled.ul`
  list-style: none;
  padding: 0;
  margin-top: 10px;
`;

export const OccupiedIntervalItem = styled.li`
  padding: 5px 10px;
  border-radius: 4px;
  background-color: ${({ theme }) => theme.occupiedIntervalBackground};
  color: ${({ theme }) => theme.text};
  margin-bottom: 5px;
`;