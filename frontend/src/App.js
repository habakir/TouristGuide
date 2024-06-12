import React from 'react';
import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import Login from './components/Auth/Login';
import Articles from './components/CMS/Articles';
import CreateArticle from './components/CMS/CreateArticle';
import CreateDestination from './components/CMS/CreateDestination';
import Destinations from './components/CMS/Destinations';
import EditArticle from './components/CMS/EditArticle';
import EditDestination from './components/CMS/EditDestination';
import Users from './components/CMS/Users';
import Navbar from './components/Shared/Navbar';
import { AuthProvider } from './contexts/AuthContext';
import Home from './components/Public/Home';
import MostRead from './components/Public/MostRead';
import AllDestinations from './components/Public/AllDestinations';
import DestinationArticles from './components/Public/DestinationArticles';
import ArticleDetail from './components/Public/ArticleDetail';
import ActivityArticles from './components/Public/ActivityArticles';
import './styles.css';

const App = () => {
    return (
        <AuthProvider>
            <Router>
                <Navbar />
                <Routes>
                    <Route path="/login" element={<Login />} />
                    <Route path="/destinations/manage" element={<Destinations />} /> {/* Route for managing destinations */}
                    <Route path="/destinations" element={<AllDestinations />} /> {/* Route for all destinations */}
                    <Route path="/articles" element={<Articles />} />
                    <Route path="/articles/create" element={<CreateArticle />} />
                    <Route path="/articles/edit/:id" element={<EditArticle />} />
                    <Route path="/destinations/create" element={<CreateDestination />} />
                    <Route path="/destinations/edit/:id" element={<EditDestination />} />
                    <Route path="/users" element={<Users />} />
                    <Route path="/" element={<Home />} />
                    <Route path="/most-read" element={<MostRead />} />
                    <Route path="/destinations/:id" element={<DestinationArticles />} />
                    <Route path="/articles/:id" element={<ArticleDetail />} />
                    <Route path="/activity/:activity" element={<ActivityArticles />} />
                    <Route path="*" element={<Navigate to="/" />} />
                </Routes>
            </Router>
        </AuthProvider>
    );
};

export default App;
