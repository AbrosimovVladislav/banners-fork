"use client"

import './globals.css'
import {Inter} from 'next/font/google'
import NavigationBar from "@/components/navigation-bar/NavigationBar";
import {QueryClient, QueryClientProvider} from 'react-query';

const inter = Inter({subsets: ['latin']})

const queryClient = new QueryClient();

type RootLayoutProps = {
  children: React.ReactNode;
};

export default function RootLayout({children}: RootLayoutProps) {
  return (
      <QueryClientProvider client={queryClient}>
        <html lang="en">
        <body>
        <div className="flex flex-row">
          <NavigationBar/>
          {children}
        </div>
        </body>
        </html>
      </QueryClientProvider>
  )
}
