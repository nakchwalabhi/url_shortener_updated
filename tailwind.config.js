/** @type {import('tailwindcss').Config} */
module.exports = {
    // Enable dark mode using class strategy
    darkMode: 'class',
    
    // Extend default theme with custom configurations
    theme: {
        extend: {
            // Custom color palette for enhanced dark/light mode
            colors: {
                // Custom dark mode colors
                dark: {
                    50: '#1A202C',   // Darkest background
                    100: '#2D3748',  // Slightly lighter background
                    200: '#4A5568',  // Card/component background
                    300: '#718096',  // Text and border colors
                },
                // Custom light mode colors
                light: {
                    50: '#F7FAFC',   // Lightest background
                    100: '#EDF2F7',  // Soft background
                    200: '#E2E8F0',  // Component backgrounds
                    300: '#CBD5E0',  // Border and subtle elements
                }
            },
            
            
            transitionProperty: {
                'background': 'background-color, background-image',
                'colors': 'color, background-color, border-color',
            },
            
            
            boxShadow: {
                'dark-lg': '0 10px 15px -3px rgba(0, 0, 0, 0.4), 0 4px 6px -2px rgba(0, 0, 0, 0.2)',
                'light-lg': '0 10px 15px -3px rgba(0, 0, 0, 0.1), 0 4px 6px -2px rgba(0, 0, 0, 0.05)',
            },
            
          
            backgroundImage: {
                'gradient-light': 'linear-gradient(to bottom right, #EDF2F7, #E6E6FA)',
                'gradient-dark': 'linear-gradient(to bottom right, #1A202C, #2C3E50)',
            },
            
           
            fontFamily: {
                'sans': ['Inter', 'system-ui', '-apple-system', 'BlinkMacSystemFont', 'Segoe UI', 'Roboto', 'Oxygen', 'Ubuntu', 'Cantarell', 'Open Sans', 'Helvetica Neue', 'sans-serif']
            }
        }
    },
    
    
    variants: {
        extend: {
            opacity: ['dark'],
            brightness: ['dark'],
            scale: ['active', 'group-hover'],
            transform: ['hover', 'focus']
        }
    },
    
    
    content: [
        "./resources/templates/index.html",  
        "./resources/static/js/app.js"
    ]
}