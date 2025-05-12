import React, { useState, useEffect } from 'react';
import BookingForm from './components/BookingForm';
import BookingList from './components/BookingList';
import styled, { ThemeProvider } from 'styled-components';
import { lightTheme, GlobalStyles } from './themes';

const AppContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: left;
  min-height: 100vh;
  width: 100%; 
  height: 100%;
  margin-left: 5%;
  background-color: ${({ theme }) => theme.body};
  color: ${({ theme }) => theme.text};
`;

const Header = styled.header`
  display: flex;
  align-items: center;
  width: 100%;
  max-width: 600px; 
  margin-bottom: 5px;
`;

const Title = styled.h1`
  text-align: left;
`;

const BookingFormContainer = styled.div`
  border: 1px solid ${({ theme }) => theme.borderColor};
  border-radius: 8px;
  padding: 20px;
  margin-bottom: 20px;
  width: 100%;
  max-width: 600px;
  display: flex;
  flex-direction: column;
`;

const App = () => {
    const [theme, setTheme] = useState('light');

    useEffect(() => {
        const storedTheme = localStorage.getItem('theme');
        if (storedTheme) {
            setTheme(storedTheme);
        }
    }, []);

    useEffect(() => {
        localStorage.setItem('theme', theme);
    }, [theme]);

    const currentTheme = theme === 'light' ? lightTheme : darkTheme;

    return (
        <ThemeProvider theme={currentTheme}>
            <GlobalStyles />
            <AppContainer>
                <Header>
                    <Title>Бронирование мест</Title>
                </Header>
                <BookingFormContainer>
                    <BookingForm />
                </BookingFormContainer>
                <BookingList />
            </AppContainer>
        </ThemeProvider>
    );
};

export default App;