import { Injectable } from "@angular/core";
import { environment } from "../../../environments/environment";
import { HttpClient } from "@angular/common/http";
import { PageInfo } from "../commons";

const URL = `${environment.baseUrl}/ledger`

@Injectable({providedIn: 'root'})
export class LedgerManagementService {

  constructor(private http:HttpClient) {}

  search(form:any) {
    return this.http.get<PageInfo>(URL, {params: form})
  }

  findById(code:string) {
    return this.http.get<any>(`${URL}/${code}`)
  }

  create(form:any) {
    return this.http.post<any>(URL, form)
  }

  update(code:string, form:any) {
    return this.http.put(`${URL}/${code}`, form)
  }
}
