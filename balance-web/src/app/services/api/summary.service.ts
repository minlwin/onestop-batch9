import { Injectable } from "@angular/core";
import { environment } from "../../../environments/environment";
import { HttpClient } from "@angular/common/http";

const URL = `${environment.baseUrl}/summary`

@Injectable({providedIn: 'root'})
export class SummaryService {

  constructor(private http:HttpClient) {}

  loadData() {
    return this.http.get<any>(URL)
  }
}
