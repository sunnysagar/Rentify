import React from "react";
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import { AuthProvider } from "./context/authService";
import NavBar from "./Components/NavBar";
import UserSignupPage from "./pages/Signup";
import Login from "./pages/Login";
// import UserDashboard from "./dashboard/SellerDashboard";
import Home from "./pages/Home";
import Profile from "./pages/Profile";
import ContactUs from "./pages/ContactUs";
import AboutUs from "./pages/AboutUs";
import ProtectedRoute from "./Components/ProtectedRoute";


const App = () =>{
  return(
    <Router>
    <AuthProvider>
        <NavBar/>
          <Routes>
            <Route path="/login" element = {<Login />}/>
            <Route path="/signup" element={<UserSignupPage />}/>
            <Route path="/" element={<ProtectedRoute/>}>
                <Route path="/" element={<Home />}/>
                <Route path="/profile" element={<Profile />}/>
                <Route path="/contact" element={<ContactUs />}/>
                <Route path="/about" element={<AboutUs />}/>
            </Route>
          </Routes>
    
    </AuthProvider>
    </Router>
        

  );
};

export default App;