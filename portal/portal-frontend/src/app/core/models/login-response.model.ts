export interface ILoginResponse {
    token: string,
    id: number,
    firstname?: string,
    lastname?: string,
    email?: string,
    role: string,
}