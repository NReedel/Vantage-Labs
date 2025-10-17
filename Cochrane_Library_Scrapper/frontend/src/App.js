/* 
 * Main Application Component 
 * This component serves as the root of the React application.
 * It renders the main UI structure.
 */

import React from "react";
import Home from "./pages/Home"; // Import the Home component

const App = () => {
    return (
        <div>
            {/* <h1 className="header" style="text-align: center">Cochrane Library Reviews</h1> */}
            <Home />
        </div>
    );
};

export default App;