import { useState } from 'react';

function Login({ onLogin, loading }) {
  const [mid, setMid] = useState('');
  const [mpw, setMpw] = useState('');

  const handleSubmit = (event) => {
    event.preventDefault();
    onLogin(mid, mpw);
  };

  return (
    <div className="login-shell">
      <h2>로그인</h2>
      <form onSubmit={handleSubmit}>
        <label>
          아이디
          <input
            type="text"
            value={mid}
            onChange={(event) => setMid(event.target.value)}
            required
          />
        </label>
        <label>
          비밀번호
          <input
            type="password"
            value={mpw}
            onChange={(event) => setMpw(event.target.value)}
            required
          />
        </label>
        <button type="submit" disabled={loading}>
          {loading ? '로그인 중...' : '로그인'}
        </button>
      </form>
    </div>
  );
}

export default Login;
