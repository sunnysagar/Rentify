import React, { useEffect, useState, useRef } from "react";
import axios from "axios";
import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";
import { useAuth } from "../context/authService";
import "../cssPages/BuyerDashboard.css";

const BuyerDashboard = () => {
  const [properties, setProperties] = useState([]);
  const { authToken } = useAuth();
  const stompClient = useRef(null);

  useEffect(() => {
    const fetchAllProperties = async () => {
      try {
        const response = await axios.get(
          "http://localhost:8010/rentify/property/buyer",
          {
            headers: { Authorization: `Bearer ${authToken}` },
          }
        );
        setProperties(response.data);
      } catch (error) {
        console.error("Error fetching properties: ", error);
      }
    };

    const connect = () => {
      const socket = new SockJS("http://localhost:8010/websocket");
      const client = new Client({
        webSocketFactory: () => socket,
        debug: (str) => {
          console.log(str);
        },
        reconnectDelay: 5000,
        connectHeaders: {
          Authorization: `Bearer ${authToken}`,
        },
        onConnect: (frame) => {
          console.log("conneted: " + frame);
          client.subscribe("/topic/properties", onPropertyUpdate);
        },
        onStompError: (error) => {
          console.error("STOMP error", error);
        },
      });

      // setting the client in the ref
      stompClient.current = client;
      client.activate();
    };

    const onPropertyUpdate = (message) => {
      console.log("Received message: ", message.body);

      const updatedProperty = JSON.parse(message.body).data;

      setProperties((prevProperties) => {
        // update the specific property or add it if it doesn't exit
        const propertyIndex = prevProperties.findIndex(
          (p) => p.id === updatedProperty.id
        );
        if (propertyIndex !== -1) {
          // update the existing property
          const updatedProperties = [...prevProperties];
          updatedProperties[propertyIndex] = updatedProperty;
          return updatedProperties;
        } else {
          // add new property
          return [...prevProperties, updatedProperty];
        }
      });
    };

    fetchAllProperties();
    connect();

    return () => {
      if (stompClient.current) {
        stompClient.current.deactivate();
      }
    };
  }, [authToken]);

  return (
    <div className="buyer-dashbaord">
      <h1>Available properties</h1>
      <div className="property-list">
        {properties.length > 0 ? (
          properties.map((property) => (
            <div key={property.id} className="property-card">
              {property.image && (
                <img src={property.image} alt={property.title} />
              )}

              <div className="property-card-content">
                <h2>{property.title}</h2>
                <p className="desc">{property.description}</p>
                
                <div className="property-details">
                  <div className="property-detail">
                    <strong>Address:</strong> <p>{property.address}</p>
                  </div>
                  <div className="property-detail">
                    <strong>Bedrooms:</strong> <p>{property.bedrooms}</p>
                  </div>
                  <div className="property-detail">
                    <strong>Bathrooms:</strong> <p>{property.bathrooms}</p>
                  </div>
                  <div className="property-detail">
                    <strong>Area:</strong> <p>{property.area}</p>
                  </div>
                  <div className="property-detail">
                    <strong>Price:</strong> <p>{property.price}</p>
                  </div>
                  <div className="property-detail">
                    <strong>Nearby Amenities:</strong>{" "}
                    <p>{property.nearbyAmenities}</p>
                  </div>
                  <div className="property-detail">
                    <strong>Property Type:</strong>{" "}
                    <p>{property.propertyType}</p>
                  </div>
                </div>

              </div>
            </div>
          ))
        ) : (
          <p>Loading...</p>
        )}
      </div>
    </div>
  );
};

export default BuyerDashboard;
