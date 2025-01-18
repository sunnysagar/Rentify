import App from './App';
import './index.css';

// ReactDOM.render(<App/>, document.getElementById('root'));

import { createRoot } from 'react-dom/client';
const container = document.getElementById('root');
const root = createRoot(container); // createRoot(container!) if you use TypeScript
root.render(<App/>);

