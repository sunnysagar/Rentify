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
    const [userType, setuserType] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        const token = localStorage.getItem('token');
        const storedUserType = localStorage.getItem('userType');
        if(token && storedUserType){
            setAuthToken(token);
            setuserType(storedUserType);
            setIsAuthenticated(true);

            // Navigate to the previous path or a default authenticated page
            const savedPath = sessionStorage.getItem('currentPath') || '/';
            navigate(savedPath);
        }
        
    }, []);

    const login = async(email, password) => {
        try {
            const response = await axios.post('http://localhost:8010/rentify/users/login', {email, password});
            setAuthToken(response.data.token);
            setuserType(response.data.userType);
            setIsAuthenticated(true);
            localStorage.setItem('token', response.data.token);
            localStorage.setItem('userType', response.data.userType);
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
        setuserType(null);
        setIsAuthenticated(false);
        localStorage.removeItem('token');
        localStorage.removeItem('userType');
        sessionStorage.removeItem('currentPath');
        navigate('/login');
        
    };

    return (
        <AuthContext.Provider value={{ isAuthenticated, authToken, userType, login, signup, logout }}>
            {children}
        </AuthContext.Provider>
    );
};