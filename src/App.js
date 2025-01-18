import React from "react";
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import { AuthProvider, useAuth } from "./context/authService";
import NavBar from "./Components/NavBar";
import Login from "./pages/Login";
import BuyerDashbaord from "./pages/BuyerDashbaord";
import Profile from "./pages/Profile";
import ContactUs from "./pages/ContactUs";
import AboutUs from "./pages/AboutUs";
import ProtectedRoute from "./Components/ProtectedRoute";
import UsePagePreservation from "./hooks/UsePagePreservation";

import SellerDashboard from "./dashboard/SellerDashboard";
import Signup from "./pages/Signup";
import UpdateProperty from "./dashboard/SellerUpdateProperty";


const App = () =>{

  return(
    <Router>
    <AuthProvider>
      <UsePagePreservation/>   {/* saving the currentPath so upon refresh or reload will not render to login page*/}
        <NavBar/>
          <Routes>
            <Route path="/login" element = {<Login />}/>
            <Route path="/signup" element={<Signup />}/>
            <Route path="/" element={<ProtectedRoute/>}>
                <Route path="/" element={<DashboardSelector />}/>  {/*Default route*/}
                <Route path="/profile" element={<Profile />}/>
                <Route path="/contact" element={<ContactUs />}/>
                <Route path="/about" element={<AboutUs />}/>
                <Route path="/update-property/:id" element={<UpdateProperty />} />
            </Route>
          </Routes>
    
    </AuthProvider>
    </Router>
        

  );
};

const DashboardSelector = () =>{
  const { userType } = useAuth();

  if(userType === 'Seller'){
    return <SellerDashboard/>;
  }
  else{
    return <BuyerDashbaord/>;
  }
}

export default App;