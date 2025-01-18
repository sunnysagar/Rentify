import axios from "axios";
import React, { useRef, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";
import { useAuth } from "../context/authService";

const UpdateProperty = () => {
    const { authToken } = useAuth();
    const stompClient = useRef(null);
    const location = useLocation();
    const navigate = useNavigate();
    const { property } = location.state;
    const [updatedProperty, setUpdatedProperty] = useState(property);

    const handleSubmit = async (e) => {
        e.preventDefault();
        console.log("Auth token:", authToken);
    console.log("Updated Property:", updatedProperty);
        try {
            const response = await axios.patch(`http://localhost:8010/rentify/property/update/${property.id}`, 
                updatedProperty, 
                {
                headers: { Authorization: `Bearer ${authToken}`,
                         "Content-Type": "application/json"
                 },
            }
        );
            setUpdatedProperty(response.data);

            if(response.status === 200){
                alert("Property updated successfully");
                navigate("/");
            } else{
                alert("Failed to update property");
            }
        } catch (error) {
            console.error("Error updating property:", error);
        }
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setUpdatedProperty((prev) => ({ ...prev, [name]: value }));   // ...prev -> copy the previous state
    }
    return(
        <div>
            <h1>Update The Details</h1>

            <form onSubmit={handleSubmit}>
                <input type="text" name="title" value={updatedProperty.title} onChange={handleChange} placeholder="title"/>
                <input name="description" value={updatedProperty.description} onChange={handleChange} />
                <input name="address" value={updatedProperty.address} onChange={handleChange} />

                <button type="submit">Update Property</button>
            </form>
        </div>
    );
}

export default UpdateProperty;