import { Injectable } from "@angular/core";
import { environment } from "../../../environments/environment";
import { HttpClient } from "@angular/common/http";
import { PageInfo } from "../commons";

const URL = `${environment.baseUrl}/entry`

@Injectable({providedIn: 'root'})
export class LedgerEntryService {

  constructor(private http:HttpClient) {}

  search(type:string, form:any) {
    return this.http.get<PageInfo>(`${URL}/${type}`, {params: form})
  }

  findById(id:string) {
    return this.http.get<any>(`${URL}/details/${id}`)
  }

  create(type:string, form:any) {
    return this.http.post<any>(`${URL}/${type}`, form)
  }
}
