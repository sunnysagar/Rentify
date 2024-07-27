import React, { createContext, useContext, useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

const AuthContext = createContext();

export const useAuth = () =>{
    return useContext(AuthContext);
};

export const AuthProvider = ({ children }) => {
    const [isAuthenticated, setIsAuthenticated] = useState(false);
    const [authToken, setAuthToken] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        const token = localStorage.getItem('authToken');
        if(token){
            setAuthToken(token);
            setIsAuthenticated(true);
        }
    }, []);

    const login = async(email, password) => {
        try {
            const response = await axios.post('http://localhost:8010/rentify/users/login', {email, password});
            setAuthToken(response.data.token);
            setIsAuthenticated(true);
            localStorage.setItem('authToken', response.data.token);
            navigate('/');
        } catch (error) {
            console.error('Login failed', error);
        }
    };

    const signup = async (user) => {
        try {
            await axios.post('http://localhost:8010/rentify/users/signup', user);
            navigate('/login');
        } catch (error) {
            console.error('Signup failed', error);
        }
    };

    const logout = () =>{
        setAuthToken(null);
        setIsAuthenticated(false);
        localStorage.removeItem('token');
        navigate('/login');
        
    };

    return (
        <AuthContext.Provider value={{ isAuthenticated, authToken, login, signup, logout }}>
            {children}
        </AuthContext.Provider>
    );
};