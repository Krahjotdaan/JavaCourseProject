import React, { useState, useEffect } from 'react';
import BookingForm from './components/BookingForm';
import BookingList from './components/BookingList';
import { ThemeProvider } from 'styled-components';
import { 
    AppContainer,
    Header,
    Title,
 } from './styles/App.styled';
import { lightTheme, GlobalStyles } from './styles/themes';
import { BookingFormContainer } from './styles/BookingForm.styled';
import { BookingListContainer } from './styles/BookingList.styled';



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
                <BookingListContainer>
                    <BookingList />
                </BookingListContainer>
            </AppContainer>
        </ThemeProvider>
    );
};

export default App;