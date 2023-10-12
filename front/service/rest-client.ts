export const post = async (url: string, data: any) => {
  const response = await fetch(url,
      {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Access-Control-Allow-Origin': '*'
        },
        body: JSON.stringify(data),
      }
  );
  if (!response.ok) {
    const errorText = await response.text();
    throw new Error(`${errorText}`);
  }

  return response.json();
}

export const get = async (url: string) => {
  const response = await fetch(url, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*'
    }
  });

  if (!response.ok) {
    const errorText = await response.text();
    throw new Error(`${errorText}`);
  }

  return response.json();
};

export const getFile = async (url: string) => {
  const response = await fetch(url, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*'
    }
  });

  if (!response.ok) {
    const errorText = await response.text();
    throw new Error(`${errorText}`);
  }

  return response.blob();
};