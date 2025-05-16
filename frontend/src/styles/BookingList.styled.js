import styled from 'styled-components';

export const BookingListContainer = styled.div`
  width: 100%;
  max-width: 600px;
  box-sizing: border-box;
  align-items: flex-start;
`;

export const BookingItem = styled.li`
  padding: 5px 10px;
  border-radius: 4px;
  background-color: ${({ theme }) => theme.occupiedIntervalBackground};
  color: ${({ theme }) => theme.text};
  margin-bottom: 5px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-wrap: wrap; 
`;

export const BookingInfo = styled.span`
  flex-basis: 70%;
  word-break: break-word;
`;

export const DeleteButton = styled.button`
  padding: 5px 10px;
  border: none;
  border-radius: 4px;
  background-color: #f44336;
  color: white;
  cursor: pointer;
  flex-basis: 25%; 
  margin-top: 5px;

  &:hover {
    background-color: #d32f2f;
  }
`;

export const SearchContainer = styled.div`
  display: flex;
  align-items: center;
  margin-bottom: 10px;
`;

export const SearchInput = styled.input`
  padding: 8px;
  border: 1px solid ${({ theme }) => theme.borderColor};
  border-radius: 4px;
  margin-right: 10px;
  flex-grow: 1;
  color: ${({ theme }) => theme.text};
  background-color: ${({ theme }) => theme.inputBackground};
  margin-bottom: 5px; 
`;

export const SearchButton = styled.button`
  padding: 8px 12px;
  border: none;
  border-radius: 4px;
  background-color: ${({ theme }) => theme.toggleButtonBackground};
  color: ${({ theme }) => theme.buttonText};
  cursor: pointer;
  margin-bottom: 5px; 
`;