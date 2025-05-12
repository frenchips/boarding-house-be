package com.adk.kost.service;

import com.adk.kost.dto.RoomDto;
import com.adk.kost.entity.Room;
import com.adk.kost.exception.ValidateException;
import com.adk.kost.repository.RoomRepository;
import com.adk.kost.request.roomRequest.DeleteDetailRoomRequest;
import com.adk.kost.request.roomRequest.DeleteRoomRequest;
import com.adk.kost.request.roomRequest.RoomRequest;
import com.adk.kost.request.roomRequest.SearchRoomRequest;

import com.adk.kost.response.Response;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomServiceImpl extends BaseServiceImpl implements RoomService {

    @Autowired
    RoomRepository roomDaoMap;


    public Room create(RoomRequest request){

        Room rm = new Room();


        validate(request);

        rm.setRoomNo(request.getRoomNo());
        rm.setPrice(request.getPrice());
        rm.setRoomStatus("NOT_EXIST");

        this.createAudit(rm, "admin");

        roomDaoMap.save(rm);
        return rm;
    }

    @Override
    public Room delete(String id) {

        Room rm = null;
        if(!id.isEmpty()){
            Long room = roomDaoMap.getById(id);

            if(room != null){
                rm = roomDaoMap.findById(room).orElse(null);
            } else {
                throw new ValidateException("Nomor kamar tidak ditemukan");
            }
        }

        roomDaoMap.delete(rm);
        return rm;
    }

    @Override
    public List<RoomDto> search(SearchRoomRequest request) {

        List<Object[]> listData = roomDaoMap.search(request.getSearchVal());


        List<RoomDto> list = new ArrayList<>();
        if(!listData.isEmpty()){

            for(Object[] obj : listData){

                RoomDto room = new RoomDto();

                room.setRoomNo(obj[0] != null ? (String) obj[0]  : null);
                room.setPrice(obj[1] != null ? (Integer) obj[1] : null);

                list.add(room);
            }
        }

        return list;
    }

    @Override
    public void deleteDetail(DeleteDetailRoomRequest request) {
        List<DeleteRoomRequest> listRequest = new ArrayList<>();
        Room rm = null;
        if(!request.getListDelete().isEmpty()){
            for(int i = 0; i < request.getListDelete().size(); i++){
                DeleteRoomRequest req = request.getListDelete().get(i);

                Long room = roomDaoMap.getById(req.getNo());

                if(room != null){
                    rm  = roomDaoMap.findById(room).orElse(null);
                }
                listRequest.add(req);
                roomDaoMap.delete(rm);
            }
        }


    }

    @Override
    public Long byId(String no) {
        Long id = roomDaoMap.getById(no);
        return id;
    }

    @Override
    public RoomDto byRoomAndPrice(String no) {

        Long id = byId(no);

        List<Object[]> listObject = roomDaoMap.getRoomPrice(id);

        List<RoomDto> listData = new ArrayList<>();

        RoomDto dto = null;
        for(Object[] obj : listObject){

            dto = new RoomDto();

            dto.setRoomNo(obj[0] != null ? (String) obj[0] : null);
            dto.setPrice(obj[1] != null ? (Integer) obj[1] : null);
            listData.add(dto);
        }

        return dto;
    }

    @Override
    public Room getLoaded(String no) {
        Long id  = byId(no);
        Room rm = roomDaoMap.findById(id).orElse(null);

        return rm;
    }

    private String validate(RoomRequest request){

        String message = "";

        String roomNo = roomDaoMap.getRoomNo(request.getRoomNo());
        if(roomNo != null){
            throw new ValidateException("nomor kamar sudah ada");
        }



        if(StringUtils.isEmpty(request.getRoomNo())){
            throw new ValidateException(String.format(" %s Tidak boleh kosong", request.getRoomNo()));
        }


        // Validate length
        if(request.getRoomNo().length() > 64){
            throw new ValidateException(String.format(" %s tidak boleh lebih dari 64 ", request.getRoomNo()));
        }



        if(StringUtils.isNotEmpty(request.getPrice().toString())){
            if (request.getPrice() < 0){
                message = "harga kamar tidak boleh lebih kecil dari 0";
                throw new ValidateException(message);
            }
        } else {
            message = "harga kamar tidak boleh kosong";
            throw  new ValidateException(message);
        }

        if(StringUtils.isEmpty(String.valueOf(request.getPrice()))){
            throw new ValidateException(String.format(" %s Tidak boleh kosong", request.getPrice()));
        }


        return message;

    }
}
