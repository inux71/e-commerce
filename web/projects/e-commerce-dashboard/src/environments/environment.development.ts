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
      update: 'dashboard/category',
    },
    employee: {
      changePassword: 'dashboard/employee/change-password',
    },
    product: {
      create: 'dashboard/product',
      products: 'dashboard/product',
    },
  },
};
