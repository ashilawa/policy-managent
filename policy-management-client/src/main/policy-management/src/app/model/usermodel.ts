import { IPolicy } from "./policymodel";

export interface IUser {

    userId : Int16Array,
    username : string,
    email : string,
    password : string,
    roles :  string [],
    policies : IPolicy []


}