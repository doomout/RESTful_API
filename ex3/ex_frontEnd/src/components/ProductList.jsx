function ProductList({ products, loading }) {
  if (loading) {
    return <div className="product-list">로딩 중...</div>;
  }

  if (!products.length) {
    return <div className="product-list">상품이 없습니다.</div>;
  }

  return (
    <div className="product-list">
      {products.map((product) => (
        <div key={product.pno} className="product-card">
          <div className="product-info">
            <h3>{product.title || '제목 없음'}</h3>
            <p>{product.description || '설명이 없습니다.'}</p>
            <div className="meta-row">
              <span>작성자: {product.writer}</span>
              <span>가격: {product.price ?? '정보 없음'}</span>
            </div>
          </div>
        </div>
      ))}
    </div>
  );
}

export default ProductList;
