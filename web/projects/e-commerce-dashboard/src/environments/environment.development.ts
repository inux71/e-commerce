export const environment = {
  production: false,
  baseUrl: 'http://localhost:8080/api',
  endpoints: {
    auth: {
      login: 'dashboard/auth/login',
    },
    category: {
      categories: 'category',
      create: 'dashboard/category',
    },
  },
};
