package controller;

import entity.Prison;
import entity.Prisoner;
import entity.Visitor;
import utils.XMLUtils;

import java.util.List;
import java.util.stream.Collectors;

public class PrisonController {
    private List<Prison> prisons;
    private List<Prisoner> prisoners;
    private List<Visitor> visitors;
    private static final String PRISONS_FILE = "src/main/resources/prison_list.xml";
    private static final String PRISONERS_FILE = "src/main/resources/prison_data.xml";
    private static final String VISITORS_FILE = "src/main/resources/visitor_data.xml";

    public PrisonController() {
        prisons = XMLUtils.readPrisonsFromXML(PRISONS_FILE);
        prisoners = XMLUtils.readPrisonersFromXML(PRISONERS_FILE);
        visitors = XMLUtils.readVisitorsFromXML(VISITORS_FILE);
    }

    public List<Prison> getAllPrisons() {
        return prisons;
    }

    public List<Prisoner> getAllPrisoners() {
        return prisoners;
    }

    public List<Visitor> getAllVisitors() {
        return visitors;
    }

    public List<Prisoner> getPrisonersByPrison(String prisonName) {
        return prisoners.stream()
                .filter(prisoner -> prisoner.getPrison().equals(prisonName))
                .collect(Collectors.toList());
    }

    public List<Visitor> getVisitorsByPrisoner(String prisonerId) {
        return visitors.stream()
                .filter(visitor -> visitor.getPrisonerId().equals(prisonerId))
                .collect(Collectors.toList());
    }

    public void addPrison(Prison prison) {
        prisons.add(prison);
        XMLUtils.writePrisonsToXML(prisons, PRISONS_FILE);
    }

    public void addPrisoner(Prisoner prisoner) {
        prisoners.add(prisoner);
        XMLUtils.writePrisonersToXML(prisoners, PRISONERS_FILE);
    }

    public void addVisitor(Visitor visitor) {
        visitors.add(visitor);
        XMLUtils.writeVisitorsToXML(visitors, VISITORS_FILE);
    }

    public void deletePrison(String prisonName) {
        // Xóa nhà tù khỏi danh sách
        prisons.removeIf(prison -> prison.getName().equals(prisonName));
        // Lưu lại dữ liệu vào file XML
        XMLUtils.writePrisonsToXML(prisons, PRISONS_FILE);
    }

    public void deletePrisoner(String prisonerId) {
        // Xóa tù nhân khỏi danh sách
        prisoners.removeIf(prisoner -> prisoner.getId().equals(prisonerId));
        // Cũng cần xóa những người đến thăm liên quan đến tù nhân này
        visitors.removeIf(visitor -> visitor.getPrisonerId().equals(prisonerId));
        // Lưu lại dữ liệu vào file XML
        XMLUtils.writePrisonersToXML(prisoners, PRISONERS_FILE);
        XMLUtils.writeVisitorsToXML(visitors, VISITORS_FILE);
    }

    public void deleteVisitor(String visitorId) {
        // Xóa người đến thăm khỏi danh sách
        visitors.removeIf(visitor -> visitor.getId().equals(visitorId));
        // Lưu lại dữ liệu vào file XML
        XMLUtils.writeVisitorsToXML(visitors, VISITORS_FILE);
    }


    public Visitor getVisitorById(String visitorId) {
        return visitors.stream()
                .filter(visitor -> visitor.getId().equals(visitorId))
                .findFirst()
                .orElse(null);
    }

    public void updateVisitor(Visitor updatedVisitor) {
        // Tìm và cập nhật thông tin của người đến thăm
        for (int i = 0; i < visitors.size(); i++) {
            Visitor currentVisitor = visitors.get(i);
            if (currentVisitor.getId().equals(updatedVisitor.getId())) {
                visitors.set(i, updatedVisitor);
                // Lưu lại dữ liệu vào file XML
                XMLUtils.writeVisitorsToXML(visitors, VISITORS_FILE);
                return;
            }
        }
    }

    public Prison getPrisonByName(String prisonName) {
        return prisons.stream()
                .filter(prison -> prison.getName().equals(prisonName))
                .findFirst()
                .orElse(null);
    }

    public void updatePrison(Prison updatedPrison) {
        // Tìm và cập nhật thông tin của nhà tù
        for (int i = 0; i < prisons.size(); i++) {
            Prison currentPrison = prisons.get(i);
            if (currentPrison.getName().equals(updatedPrison.getName())) {
                prisons.set(i, updatedPrison);
                // Lưu lại dữ liệu vào file XML
                XMLUtils.writePrisonsToXML(prisons, PRISONS_FILE);
                return;
            }
        }
    }

    public Prisoner getPrisonerById(String prisonerId) {
        return prisoners.stream()
                .filter(prisoner -> prisoner.getId().equals(prisonerId))
                .findFirst()
                .orElse(null);
    }

    public void updatePrisoner(Prisoner updatedPrisoner) {
        // Tìm và cập nhật thông tin của tù nhân
        for (int i = 0; i < prisoners.size(); i++) {
            Prisoner currentPrisoner = prisoners.get(i);
            if (currentPrisoner.getId().equals(updatedPrisoner.getId())) {
                prisoners.set(i, updatedPrisoner);
                // Lưu lại dữ liệu vào file XML
                XMLUtils.writePrisonersToXML(prisoners, PRISONERS_FILE);
                return;
            }
        }
    }
}
