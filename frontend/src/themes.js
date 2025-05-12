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
    body: '#363537',
    text: '#FAFAFA',
    toggleButtonBackground: '#BB86FC',
    buttonText: '#fff',
    borderColor: '#666',
    inputBackground: '#444',
    successText: '#90EE90',
    successBackground: '#228B22',
    errorText: '#F08080',
    errorBackground: '#8B0000',
    availableSlotBackground: '#444',
    occupiedSlotBackground: '#222',
    occupiedIntervalBackground: '#333'
};

export const GlobalStyles = createGlobalStyle`
  body {
    background: ${({ theme }) => theme.body};
    color: ${({ theme }) => theme.text};
    transition: all 0.2s linear;
  }
`;