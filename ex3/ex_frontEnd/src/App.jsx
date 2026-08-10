import { useEffect, useState } from 'react';
import Login from './components/Login';
import ProductList from './components/ProductList';

const API_BASE = import.meta.env.VITE_API_BASE || 'http://localhost:8080';

function App() {
  const [token, setToken] = useState(localStorage.getItem('accessToken') || '');
  const [products, setProducts] = useState([]);
  const [page, setPage] = useState(1);
  const [totalPages, setTotalPages] = useState(1);
  const [message, setMessage] = useState('');
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    if (token) {
      loadProducts(page);
    }
  }, [token, page]);

  const handleLogin = async (mid, mpw) => {
    setMessage('');
    setLoading(true);

    try {
      const response = await fetch(`${API_BASE}/api/v1/token/make`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({ mid, mpw })
      });

      const data = await response.json();

      if (!response.ok) {
        setMessage(data.error || '로그인에 실패했습니다.');
        return;
      }

      setToken(data.accessToken);
      localStorage.setItem('accessToken', data.accessToken);
      setMessage('로그인 성공! 상품 목록을 불러옵니다.');
    } catch (error) {
      setMessage('네트워크 오류가 발생했습니다.');
    } finally {
      setLoading(false);
    }
  };

  const loadProducts = async (pageNumber) => {
    setMessage('');
    setLoading(true);

    try {
      const response = await fetch(
        `${API_BASE}/api/v1/products/list?page=${pageNumber}&size=10`,
        {
          headers: {
            Authorization: `Bearer ${token}`
          }
        }
      );

      const data = await response.json();

      if (!response.ok) {
        setMessage(data.error || '상품 목록을 불러오지 못했습니다.');
        return;
      }

      setProducts(data.content || []);
      setTotalPages(data.totalPages || 1);
    } catch (error) {
      setMessage('상품 목록 로드 중 오류가 발생했습니다.');
    } finally {
      setLoading(false);
    }
  };

  const handleLogout = () => {
    setToken('');
    localStorage.removeItem('accessToken');
    setProducts([]);
    setMessage('로그아웃 되었습니다.');
  };

  return (
    <div className="app-shell">
      <header>
        <h1>EX Font</h1>
        <p>간단한 React 프론트엔드 예제</p>
      </header>

      {token ? (
        <main>
          <div className="toolbar">
            <button onClick={handleLogout}>로그아웃</button>
            <div className="page-controls">
              <button onClick={() => setPage((prev) => Math.max(prev - 1, 1))} disabled={page <= 1}>
                이전
              </button>
              <span>페이지 {page} / {totalPages}</span>
              <button onClick={() => setPage((prev) => Math.min(prev + 1, totalPages))} disabled={page >= totalPages}>
                다음
              </button>
            </div>
          </div>

          <ProductList products={products} loading={loading} />
        </main>
      ) : (
        <Login onLogin={handleLogin} loading={loading} />
      )}

      {message && <div className="message">{message}</div>}
    </div>
  );
}

export default App;
