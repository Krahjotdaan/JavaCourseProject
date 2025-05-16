import styled from 'styled-components';

export const AppContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  min-height: 100vh;
  width: 100%;
  height: 100%;
  padding-left: 5%;
  padding-right: 5%;
  background-color: ${({ theme }) => theme.body};
  color: ${({ theme }) => theme.text};
  box-sizing: border-box;
`;

export const Header = styled.header`
  display: flex;
  align-items: center;
  width: 100%;
  max-width: 600px;
  margin-bottom: 5px;
  font-size: calc(1rem + 2vw);

  @media (min-width: 1200px) {
    font-size: 24px; /* Максимальный размер */
  }
`;

export const Title = styled.h1`
  text-align: left;
  font-size: 2em; 
`;