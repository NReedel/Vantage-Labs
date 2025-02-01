import React, { useState } from "react";

const SearchBox = ({ data, onSearch }) => {
  const [searchTerm, setSearchTerm] = useState("");
  const [suggestions, setSuggestions] = useState([]);

  const handleSearchChange = (event) => {
    const query = event.target.value;
    setSearchTerm(query);

    if (query.length > 0) {
      const filteredSuggestions = data
        .filter((item) => item.toLowerCase().includes(query.toLowerCase()))
        .slice(0, 5);
      setSuggestions(filteredSuggestions);
    } else {
      setSuggestions([]);
    }

    onSearch(query);
  };

  return (
    <div>
      <input
        type="text"
        placeholder="Search reviews..."
        value={searchTerm}
        onChange={handleSearchChange}
        style={{ borderColor: "#962d91", padding: "8px", width: "100%" }}
      />
      <ul style={{ listStyleType: "none", padding: 0, color: "blue" }}>
        {suggestions.map((suggestion, index) => (
          <li key={index}>{suggestion}</li>
        ))}
      </ul>
    </div>
  );
};

export default SearchBox;
