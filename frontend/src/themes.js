import { createGlobalStyle } from 'styled-components';

export const lightTheme = {
    body: '#fff',
    text: '#363537',
    toggleButtonBackground: '#4CAF50',
    buttonText: '#fff',
    borderColor: '#ccc',
    inputBackground: '#f9f9f9',
    successText: '#155724',
    successBackground: '#d4edda',
    errorText: '#721c24',
    errorBackground: '#f8d7da',
    availableSlotBackground: '#ffffff',
    occupiedSlotBackground: '#cccccc',
    occupiedIntervalBackground: '#f2f2f2'
};

export const darkTheme = {
    body: '#121212',
    text: '#FAFAFA', 
    toggleButtonBackground: '#BB86FC',
    buttonText: '#000',
    borderColor: '#555', 
    inputBackground: '#333', 
    successText: '#81C784', 
    successBackground: '#388E3C', 
    errorText: '#E57373', 
    errorBackground: '#D32F2F', 
    availableSlotBackground: '#424242', 
    occupiedSlotBackground: '#757575', 
    occupiedIntervalBackground: '#616161'
};

export const GlobalStyles = createGlobalStyle`
  body {
    background: ${({ theme }) => theme.body};
    color: ${({ theme }) => theme.text};
    transition: all 0.2s linear;
  }
`;