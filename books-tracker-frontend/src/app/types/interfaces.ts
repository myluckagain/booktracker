export interface Author {
    id?: string;
    name: string;
}

export interface Genre {
    id?: string;
    name: string;
}

export interface Book {
    id?: string;
    name: string;
    authors: Author[];
    genres: Genre[];
    visit: Visit;
    url: string;
}

export interface Visit {
    id?: string;
    date: Date;
    success: boolean;
    booksAdded: number;
}

export interface User {
    id?: string;
    name: string;
    password: string;
    role?: string;
}

export interface AuthResponse {
    jwtToken: string;
}

export interface MyProfileDto {
    email?: string;
    password?: string;
}

export interface Page<C> {
    content: C;
    totalPages: number;

}
